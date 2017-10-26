/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  antonio.cmajunior
 * Created: 25/10/2017
 */
idVenda INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),



idVendavel INTEGER,
FOREIGN KEY (idVendavel) REFERENCES Vendavel_template (IdVendavel)
