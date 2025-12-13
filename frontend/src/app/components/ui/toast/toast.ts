import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ToastTypes } from '../../../../@types/types';

@Component({
  selector: 'app-toast',
  imports: [],
  templateUrl: './toast.html',
  styleUrl: './toast.scss',
  standalone: true,
})
export class Toast {
  @Input()
  toastType: ToastTypes | null = null;

  @Input()
  content: string | null = null;

  @Input()
  title: string | null = null;
}
