import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ToastService } from '../../../services/toast-service';
import { ToastTypes } from '../../../../@types/types';

@Component({
  selector: 'app-toast',
  standalone: true,
  templateUrl: './toast.html',
  styleUrl: './toast.scss',
})
export class Toast implements OnInit, OnDestroy {
  toastType: ToastTypes | null = null;
  content: string | null = null;
  title: string | null = null;

  get isVisible(): boolean {
    return !!this.toastType;
  }

  private sub!: Subscription;

  constructor(private toastService: ToastService) {}

  ngOnInit() {
    this.sub = this.toastService.toast$.subscribe((toast) => {
      if (!toast) {
        this.toastType = null;
        this.title = null;
        this.content = null;
        return;
      }

      this.toastType = toast.type;
      this.title = toast.title;
      this.content = toast.content;
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
