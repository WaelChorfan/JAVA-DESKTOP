/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.FosUser;
import java.util.List;
import entities.Produit;

/**
 *
 * @author Wassim
 */
public interface IProduitService {

    public void ajouterProduit(Produit p);

    public void modifProduit(Produit p, String newNom, String newDescription, Double newPrix);

    public void modifProduit(Produit p, String newNom, String newDescription, Double newPrix, String newImageName);

    public void supprProduit(Produit p);

    public List<Produit> afficherProduits();

    public List<Produit> afficherProduitsParType(String type);

    public List<Produit> afficherProduitsParCategorie(String cat);

    public List<Produit> afficherProduitsParTypeEtUser(String s,FosUser u);

    public List<Produit> afficherProduitsParCategorieEtUser(String s,FosUser u);


    public Produit getProduitByUser(FosUser u);

}
