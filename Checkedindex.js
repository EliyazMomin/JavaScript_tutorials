const myCheckBox = document.getElementById("myCheckBox");
const IphoneProMax = document.getElementById("IphoneProMax");
const IphonePro = document.getElementById("IphonePro");
const IphonePlus = document.getElementById("IphonePlus");
const Iphone = document.getElementById("Iphone");
const mysubmit = document.getElementById("mysubmit");
const subResult = document.getElementById("subResult");
const modelresult = document.getElementById("modelresult");

mysubmit.onclick = function() { 
    if (myCheckBox.checked) {
        subResult.textContent = 'You are subscribed!';
    } else {
        subResult.textContent = 'You are Not subscribed!';
    }

    if(IphoneProMax.checked){
        modelresult.textContent = 'You have selected Iphone Pro Max';
    }else if(IphonePro){
        modelresult.textContent = 'You have selected Iphone Pro';
    }else if(IphonePlus){
        modelresult.textContent = 'You have selected Iphone Plus';
    }else if(Iphone){
        modelresult.textContent = 'You have selected Iphone';
    }


}