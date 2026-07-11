import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MedicalRecordService } from '../../../services/medical-record.service';
import { MedicalRecordResponse } from '../../../interfaces/entities/medical-record.interface';
import { AuthService } from '../../../services/auth.service';
import { Role } from '../../../../shared/enums/role.enum';
import { NgIcon } from '@ng-icons/core';

@Component({
  selector: 'app-record-list',
  standalone: true,
  imports: [DatePipe, RouterLink, NgIcon],
  templateUrl: './record-list.html',
})
export class RecordListComponent implements OnInit {
  private medicalRecordService = inject(MedicalRecordService);
  private authService = inject(AuthService);
  isAdmin = computed(() => this.authService.hasAnyRole([Role.ADMIN]));

  records = signal<MedicalRecordResponse[]>([]);
  loading = signal(false);
  errorMessage = signal<string | null>(null);

  ngOnInit(): void {
    this.loading.set(true);
    this.medicalRecordService.getAllMedicalRecords().subscribe({
      next: (res) => {
        this.records.set(
          (res.data ?? []).sort(
            (a, b) => new Date(b.recordDate).getTime() - new Date(a.recordDate).getTime(),
          ),
        );
        this.loading.set(false);
      },
      error: () => {
        this.errorMessage.set('No se pudieron cargar los registros médicos.');
        this.loading.set(false);
      },
    });
  }
}
