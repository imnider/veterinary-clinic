import { Component, computed, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { getNavItemsForRoles } from '../nav-items.config';
import { Topbar } from '../topbar/topbar';
import { BottomNav } from '../bottom-nav/bottom-nav';
import { AuthService } from '../../../features/services/auth.service';

@Component({
  selector: 'app-main-layout',
  standalone: true,
  imports: [RouterOutlet, Topbar, BottomNav],
  templateUrl: './main-layout.html',
})
export class MainLayout {
  private readonly authService = inject(AuthService);

  readonly navItems = computed(() =>
    getNavItemsForRoles(this.authService.currentUser()?.roles ?? []),
  );
}
