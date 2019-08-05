import {NotifyType} from './NotifyType';

export interface DogNotification {
  id: number;
  message: string;
  notifyType: NotifyType;
}
