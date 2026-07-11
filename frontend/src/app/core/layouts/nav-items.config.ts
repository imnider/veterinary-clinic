import { NavItem } from '../../features/interfaces/models/nav-item.interface';
import { Role } from '../../shared/enums/role.enum';

const OWNER_NAV: NavItem[] = [
  { label: 'Inicio', icon: 'heroHome', route: '/home' },
  { label: 'Mascotas', icon: 'heroHeart', route: '/pets' },
  { label: 'Citas', icon: 'heroCalendarDays', route: '/appointments' },
  { label: 'Registros', icon: 'heroFolder', route: '/records' },
];

const VET_NAV: NavItem[] = [
  { label: 'Inicio', icon: 'heroHome', route: '/home' },
  { label: 'Pacientes', icon: 'heroClipboardDocumentList', route: '/pets' },
  { label: 'Citas', icon: 'heroCalendarDays', route: '/appointments' },
  { label: 'Registros', icon: 'heroFolder', route: '/records' },
];

export function getNavItemsForRoles(roles: Role[]): NavItem[] {
  if (roles.includes(Role.VETERINARIO) || roles.includes(Role.ADMIN)) {
    return VET_NAV;
  }
  return OWNER_NAV;
}
