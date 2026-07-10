import { Component, inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-splash',
  standalone: true,
  templateUrl: './splash.html',
})
export class Splash implements OnInit {
  private readonly router = inject(Router);
  private readonly authService = inject(AuthService);

  ngOnInit(): void {
    setTimeout(() => {
      const destination = this.authService.isAuthenticated() ? '/home' : '/login';
      this.router.navigate([destination]);
    }, 1800);
  }
}
