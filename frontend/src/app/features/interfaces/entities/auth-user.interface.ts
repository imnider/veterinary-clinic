import { Role } from '../../../shared/enums/role.enum';

export interface AuthUser {
  id: number;
  username: string;
  roles: Role[];
  permissions: string[];
}
