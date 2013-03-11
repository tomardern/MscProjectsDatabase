$(document).ready(function(){
    
    
   /* Random Text Generator */
   alpha = "abcdefghijklmnopqrstuvwxyz";
   randText = "";
   
   for(i=0; i<10; i++) {
    num = Math.floor(Math.random()*alpha.length);
    randText += alpha[num];
   }
 
   $(".auto input[type='text'], textarea").each(function(){
       if ($(this).val().length < 1){
           $(this).val(randText);
       }
   });
    
   $(".date").val("22-Feb-2015");
    
    
    
    
});