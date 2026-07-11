import { Component, Input } from '@angular/core';
import { AppointmentResponse } from '../../../features/interfaces/entities/appointment.interface';
import { DatePipe } from '@angular/common';
import { getStatusOption } from '../../constants/appointment.constants';

@Component({
  selector: 'app-appointment-card',
  standalone: true,
  templateUrl: './appointment-card.html',
  imports: [DatePipe],
})
export class AppointmentCardComponent {
  @Input({ required: true }) appointment!: AppointmentResponse;

  readonly getStatusOption = getStatusOption;
}
