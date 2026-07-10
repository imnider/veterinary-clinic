import { Component, Input } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { NgIcon } from '@ng-icons/core';
import { NavItem } from '../../../features/interfaces/models/nav-item.interface';

@Component({
  selector: 'app-bottom-nav',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, NgIcon],
  templateUrl: './bottom-nav.html',
})
export class BottomNav {
  @Input({ required: true }) items: NavItem[] = [];
}
