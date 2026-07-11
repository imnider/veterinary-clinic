export interface VeterinarianOption {
  id: number;
  name: string;
}

export const VETERINARIANS: VeterinarianOption[] = [
  { id: 1, name: 'Dr. Carlos Mendoza' },
  { id: 2, name: 'Dra. Andrea Solís' },
  { id: 3, name: 'Dr. Luis Paredes' },
];

export interface SelectOption {
  value: string;
  label: string;
}

export const APPOINTMENT_TYPES: SelectOption[] = [
  { value: 'CONSULTA', label: 'Consulta general' },
  { value: 'VACUNACION', label: 'Vacunación' },
  { value: 'CIRUGIA', label: 'Cirugía' },
  { value: 'EMERGENCIA', label: 'Emergencia' },
];

export interface AppointmentStatusOption extends SelectOption {
  badgeClass: string;
}

export const APPOINTMENT_STATUSES: AppointmentStatusOption[] = [
  { value: 'PENDIENTE', label: 'Pendiente', badgeClass: 'badge-warning' },
  { value: 'CONFIRMADA', label: 'Confirmada', badgeClass: 'badge-info' },
  { value: 'ATENDIDA', label: 'Atendida', badgeClass: 'badge-success' },
  { value: 'CANCELADA', label: 'Cancelada', badgeClass: 'badge-error' },
];

export function getAppointmentTypeLabel(value: string): string {
  return APPOINTMENT_TYPES.find((t) => t.value === value)?.label ?? value;
}

export function getStatusOption(value: string): AppointmentStatusOption | undefined {
  return APPOINTMENT_STATUSES.find((s) => s.value === value);
}

export const PET_SPECIES: SelectOption[] = [
  { value: 'PERRO', label: 'Perro' },
  { value: 'GATO', label: 'Gato' },
  { value: 'AVE', label: 'Ave' },
  { value: 'OTRO', label: 'Otro' },
];

export const PET_SEX: SelectOption[] = [
  { value: 'MACHO', label: 'Macho' },
  { value: 'HEMBRA', label: 'Hembra' },
];
