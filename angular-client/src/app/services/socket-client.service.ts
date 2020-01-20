import {Injectable, OnDestroy} from '@angular/core';
import {SocketClientState} from '../models/SocketClientState';
import {BehaviorSubject, Observable} from 'rxjs';
import {Client, Message, over} from 'stompjs';
import {filter, first, switchMap} from 'rxjs/operators';
import {StompSubscription} from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';



@Injectable({
  providedIn: 'root'
})
export class SocketClientService implements OnDestroy {

  constructor() {
    this.client = over(new SockJS('http://localhost:8080/websocket'));
    this.state = new BehaviorSubject<SocketClientState>(SocketClientState.ATTEMPTING);
    this.client.connect({}, () => {
      this.state.next(SocketClientState.CONNECTED);
    });
  }

  private client: Client;
  private state: BehaviorSubject<SocketClientState>;

  static textHandler(message: Message): string {
    return message.body;
  }

  private connect(): Observable<Client> {
    return new Observable<Client>(observer => {
      this.state.pipe(filter(state => state === SocketClientState.CONNECTED)).subscribe(() => {
        observer.next(this.client);
      });
    });
  }

  onMessage(topic: string, handler = SocketClientService.textHandler): Observable<any> {
    return this.connect().pipe(first(), switchMap(inst => {
      return new Observable<any>(observer => {
        const subscription: StompSubscription = inst.subscribe(topic, message => {
          observer.next(handler(message));
        });
        return () => inst.unsubscribe(subscription.id);
      });
    }));
  }

  send(topic: string, payload: any): void {
    this.connect()
      .pipe(first())
      .subscribe(client => client.send(topic, {}, JSON.stringify(payload)));
  }

  ngOnDestroy() {
    this.connect()
      .pipe(first())
      .subscribe(client => client.disconnect(null));
  }
}
