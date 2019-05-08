/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.FosUser;
import java.util.List;

public interface IUserService {

    public void ajouterUser(FosUser u);

    public void modifUser(FosUser u, String newUserName, String newEmail, String newPassword, String newImageName);

    public void supprUser(FosUser u);

    public List<FosUser> afficherUsers();

    public FosUser afficherUser(String username);

    public FosUser getUserByEmail(String email);



}
