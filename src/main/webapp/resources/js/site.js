$(document).ready(function(){
    
    
    /* Random Text Generator */
    alpha = "abcdefghijklmnopqrstuvwxyz";
    randText = "";
   
    for(i=0; i<10; i++) {
        num = Math.floor(Math.random()*alpha.length);
        randText += alpha[num];
    }
    
    $(".auto input[type='text'], .auto input[type='password'], textarea").each(function(){
        if ($(this).val().length < 1){
            $(this).val(randText);
        }
    });
    
    //HTML5 Date selector and today's date by default http://stackoverflow.com/questions/6982692/html5-input-type-date-default-value-to-today
    $(".date").attr("type","date").val(new Date().toJSON().substring(0,10));
    

    
    
    
    
});