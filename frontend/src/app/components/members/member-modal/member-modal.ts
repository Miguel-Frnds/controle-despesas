import { Component, EventEmitter, Input, OnChanges, Output } from '@angular/core';
import { MemberService } from '../../../services/memberService';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-member-modal',
  imports: [FormsModule],
  templateUrl: './member-modal.html',
  styleUrl: './member-modal.css',
})
export class MemberModal implements OnChanges{

  constructor(
    private memberService: MemberService
  ){}

  @Input() isOpen = false;
  @Input() mode: 'create' | 'edit' = 'create';
  @Input() member: any;

  @Output() saved = new EventEmitter();
  @Output() closed = new EventEmitter();

  name = '';

  ngOnChanges() {
    if (this.mode === 'edit' && this.member) {
      this.name = this.member.name;
    } else {
      this.name = '';
    }
  }

  saveMember(){
    if(this.mode === 'create') {
      this.memberService.saveMember(this.name).subscribe({
        next: () => {
          this.saved.emit();
          this.closed.emit();
        },
        error: (err) => {
          alert(err.error.message);
        }
      });
    } else {
      this.memberService.editMember(this.member.id, this.name).subscribe({
        next: () => {
          this.saved.emit();
          this.closed.emit();
        },
        error: (err) => {
          alert(err.error.message);
        }
      });
    }
  }

  close(){
    this.closed.emit();
  }
}
