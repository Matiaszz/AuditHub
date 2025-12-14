import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import { Router } from '@angular/router';
import { User } from '../../../@types/types';

@Component({
  selector: 'app-painel',
  imports: [],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.scss',
})
export class Dashboard implements OnInit {
  constructor(private authService: AuthService, private router: Router) {}
  user: User | null = null;
  ngOnInit(): void {
    this.user = this.authService.getUser();
    if (!this.user) {
      this.router.navigate(['/auth']);
      return;
    }
  }
}
