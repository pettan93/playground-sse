package cz.kalas.samples.dogstation.events;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Component
public class StateChangeEventPublisher implements
        ApplicationListener<StateChangeEvent>,
        Consumer<FluxSink<StateChangeEvent>> {

    @Autowired
    @Qualifier("applicationTaskExecutor")
    private TaskExecutor taskExecutor;

    private final BlockingQueue<StateChangeEvent> queue =  new LinkedBlockingQueue<>();

//    StateChangeEventPublisher(@Qualifier("asyncExecutor") TaskExecutor threadPoolTaskExecutor) {
//        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
//    }

    @Override
    public void onApplicationEvent(StateChangeEvent event) {
        this.queue.offer(event);
    }

    @Override
    public void accept(FluxSink<StateChangeEvent> sink) {
        this.taskExecutor.execute(() -> {
            while (true)
                try {
                    StateChangeEvent event = queue.take();
                    sink.next(event);
                }
                catch (InterruptedException e) {
                    ReflectionUtils.rethrowRuntimeException(e);
                }
        });
    }

}