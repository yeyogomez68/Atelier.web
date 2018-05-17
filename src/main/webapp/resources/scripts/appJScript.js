/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*Script que permite controlar la aparicion del menu*/
function ocultaryMostrarMenu(x) {
    document.getElementById("barsMenu").classList.toggle("change");                
    document.getElementById("menu").classList.toggle("asideToggle");
    document.getElementById("barsMenu").classList.toggle("containerBarMenuTogggle");
}