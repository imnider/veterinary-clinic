import { Component, computed, inject } from '@angular/core';
import { AuthService } from '../../../features/services/auth.service';

@Component({
  selector: 'app-topbar',
  standalone: true,
  imports: [],
  templateUrl: './topbar.html',
})
export class Topbar {
  private readonly authService = inject(AuthService);
  readonly username = computed(() => this.authService.currentUser()?.username ?? '');

  onLogout(): void {
    this.authService.logout();
  }
}
