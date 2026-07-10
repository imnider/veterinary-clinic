export interface AppointmentSummary {
  petName: string;
  reason: string;
  appointmentDate: string;
}

export interface AppointmentRequest {
  petId: number;
  veterinarianId: number;
  appointmentDate: string;
  reason: string;
  appointmentType: string;
}

export interface AppointmentResponse {
  id: number;
  petId: number;
  petName: string;
  veterinarianId: number;
  veterinarianName: string;
  appointmentDate: string;
  reason: string;
  appointmentType: string;
  status: string;
}
