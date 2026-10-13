import {
  Component,
  OnInit,
  inject,
  ChangeDetectorRef
} from '@angular/core';

import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AnnouncementModel } from '../../../core/models/announcement';
import { AnnouncementService } from '../../../core/services/announcement.service';

@Component({
  selector: 'app-announcement',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './announcement.html',
  styleUrl: './announcement.css'
})
export class Announcement implements OnInit {

  announcementList: AnnouncementModel[] = [];

  editingAnnouncement = false;

  newAnnouncement: AnnouncementModel = {

    id: undefined,

    title: '',

    content: '',

    target: '',

    createdById: 1

  };

  private announcementService = inject(AnnouncementService);

  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {

    this.loadAnnouncements();

  }

  loadAnnouncements(): void {

    this.announcementService
      .getAllAnnouncements()
      .subscribe({

        next: (data) => {

          console.log('Announcements:', data);

          this.announcementList = [...data];

          this.cdr.detectChanges();

        },

        error: (err) => {

          console.error(err);

        }

      });

  }

  saveAnnouncement(): void {

    if (
      !this.newAnnouncement.title.trim() ||
      !this.newAnnouncement.content.trim() ||
      !this.newAnnouncement.target.trim()
    ) {

      return;

    }

    if (this.editingAnnouncement) {

      this.announcementService
        .updateAnnouncement(
          this.newAnnouncement.id!,
          this.newAnnouncement
        )
        .subscribe({

          next: () => {

            this.resetForm();

            this.loadAnnouncements();

          },

          error: (err) => {

            console.error(err);

          }

        });

    }

    else {

      this.announcementService
        .addAnnouncement(this.newAnnouncement)
        .subscribe({

          next: () => {

            alert('Announcement Added Successfully');

            this.resetForm();

            this.loadAnnouncements();

          },

          error: (err) => {

            console.error(err);

          }

        });

    }

  }

  editAnnouncement(
    announcement: AnnouncementModel
  ): void {

    this.editingAnnouncement = true;

    this.newAnnouncement = {

      ...announcement

    };

  }

  deleteAnnouncement(
    id: number
  ): void {

    if (!confirm('Delete this announcement?')) {

      return;

    }

    this.announcementService
      .deleteAnnouncement(id)
      .subscribe({

        next: () => {

          this.loadAnnouncements();

        },

        error: (err) => {

          console.error(err);

        }

      });

  }

  resetForm(): void {

    this.editingAnnouncement = false;

    this.newAnnouncement = {

      id: undefined,

      title: '',

      content: '',

      target: '',

      createdById: 1

    };

  }

}