import { NavItem } from '../../features/interfaces/models/nav-item.interface';
import { Role } from '../../shared/enums/role.enum';

const OWNER_NAV: NavItem[] = [
  { label: 'Inicio', icon: 'home', route: '/home' },
  { label: 'Mascotas', icon: 'heart', route: '/pets' },
  { label: 'Citas', icon: 'calendar-days', route: '/appointments' },
  { label: 'Perfil', icon: 'user-circle', route: '/profile' },
];

const VET_NAV: NavItem[] = [
  { label: 'Inicio', icon: 'home', route: '/home' },
  { label: 'Pacientes', icon: 'clipboard-document-list', route: '/patients' },
  { label: 'Citas', icon: 'calendar-days', route: '/appointments' },
  { label: 'Perfil', icon: 'user-circle', route: '/profile' },
];

export function getNavItemsForRoles(roles: Role[]): NavItem[] {
  if (roles.includes(Role.VETERINARIO) || roles.includes(Role.ADMIN)) {
    return VET_NAV;
  }
  return OWNER_NAV;
}
