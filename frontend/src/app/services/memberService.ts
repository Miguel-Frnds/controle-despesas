import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class MemberService {

  private apiUrl = 'https://controle-despesas-th02.onrender.com/members';

  constructor(private http: HttpClient){}

  findMembers(active?: boolean){
    let url = this.apiUrl;

    if(active !== undefined) {
      url += `?active=${active}`;
    }

    return this.http.get(url);
  }

  saveMember(name: string){
    return this.http.post(this.apiUrl, { name });
  }

  editMember(id: number, name: string){
    return this.http.put(`${this.apiUrl}/${id}`, { name })
  }

  deactivateMember(id: number){
    return this.http.patch(`${this.apiUrl}/${id}/deactivate`, {});
  }

  activateMember(id:number) {
    return this.http.patch(`${this.apiUrl}/${id}/activate`, {});
  }
}