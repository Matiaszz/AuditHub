import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'default-button',
  imports: [],
  templateUrl: './button.html',
  styleUrl: './button.scss',
})
export class Button {
  @Input()
  content: string = '';

  @Output() onAction = new EventEmitter<void>();

  trigger() {
    this.onAction.emit();
  }
}
