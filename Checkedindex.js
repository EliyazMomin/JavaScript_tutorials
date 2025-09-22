const myCheckBox = document.getElementById("myCheckBox");
const IphoneProMax = document.getElementById("IphoneProMax");
const IphonePro = document.getElementById("IphonePro");
const IphonePlus = document.getElementById("IphonePlus");
const Iphone = document.getElementById("Iphone");
const mysubmit = document.getElementById("mysubmit");
const subResult = document.getElementById("subResult");
const modelresult = document.getElementById("modelresult");

mysubmit.onclick= function(){
    if(myCheckBox.ariaChecked){
        subResult.textContent= 'You are subscribed!';
    }else{
        subResult.textContent= 'You are Not subscribed!';
    }

   // if(){}
}