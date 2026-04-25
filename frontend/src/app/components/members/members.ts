import { ChangeDetectorRef, Component } from '@angular/core';
import { MemberService } from '../../services/memberService';
import { MemberModal } from "./member-modal/member-modal";
import { FormsModule } from "@angular/forms";

@Component({
  selector: 'app-members',
  imports: [MemberModal, FormsModule],
  templateUrl: './members.html',
  styleUrl: './members.css',
})
export class Members {

  constructor(
    private cdr: ChangeDetectorRef,
    private memberService: MemberService
  ){}

  members: any[] = [];
  membersCount: number = 0;

  showModal = false;
  modalMode: 'create' | 'edit' = 'create';
  showInactive = false;
  selectedMember: any = null;

  loadActiveMembers(){
    this.showInactive = false;

    this.memberService.findMembers(true).subscribe((data: any) => {
      this.members = data;
      this.membersCount = this.members.length;
      this.cdr.detectChanges();
    })
  }

  loadInactiveMembers() {
    this.showInactive = true;

    this.memberService.findMembers(false).subscribe((data: any) => {
      this.members = data;
      this.membersCount = this.members.length;
      this.cdr.detectChanges();
    })
  }

  reloadCurrentMembers() {
    if(this.showInactive) {
      this.loadInactiveMembers();
    } else {
      this.loadActiveMembers();
    }
  }

  toggleMembersView() {
    if(this.showInactive) {
      this.loadActiveMembers();
    } else {
      this.loadInactiveMembers();
    }
  }

  ngOnInit(): void{
    this.loadActiveMembers();
  }

  deactivateMember(id: number){
    const confirmed = confirm('Você tem certeza que deseja desativar esse membro?');

    if(!confirmed) {
      return;
    }

    this.memberService.deactivateMember(id).subscribe(() => {
      this.loadActiveMembers();
    })
  }

  activateMember(id: number){
    this.memberService.activateMember(id).subscribe(() => {
      this.loadInactiveMembers();
    })
  }

  openCreateModal(){
    this.modalMode = 'create';
    this.selectedMember = null;
    this.showModal = true;
  }

  openEditModal(member: any){
    this.modalMode = 'edit';
    this.selectedMember = member;
    this.showModal = true;
  }

  closeModal(){
    this.showModal = false;
  }
}
