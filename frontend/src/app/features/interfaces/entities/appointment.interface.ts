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

export interface CreateAppointmentRequest {
  petId: number;
  veterinarianId: number;
  appointmentDate: string;
  reason: string;
  appointmentType: string;
}

export interface UpdateAppointmentStatusRequest {
  status: string;
}
