let email = "abc@gmail.com";
let password = "abc123";

const loginText = document.querySelector(".title-text .login");
const loginForm = document.querySelector("form.login");
const loginBtn = document.querySelector("label.login");
const signupBtn = document.querySelector("label.signup");
const signupLink = document.querySelector("form .signup-link a");


signupBtn.onclick = (()=>{
  loginForm.style.marginLeft = "-50%";
  loginText.style.marginLeft = "-50%";

  document.querySelector("#loginEmail").value = "";
  document.querySelector("#loginPassword").value = "";
});

loginBtn.onclick = (()=>{
  loginForm.style.marginLeft = "0%";
  loginText.style.marginLeft = "0%";
});

signupLink.onclick = (()=>{
  signupBtn.click();
  return false;
});

let forms = document.querySelectorAll("form");

forms[0].addEventListener("submit", login);

function login(event){
  event.preventDefault();
  let mail = document.querySelector("#loginEmail");
  let pass = document.querySelector("#loginPassword");

  if(mail.value == email && pass.value == password){
    document.querySelector("#success").classList.add("open-success");
  } else {
    document.querySelector("#error").classList.add("open-error");
  }
}

function closeSuccess(){
  document.querySelector("#success").classList.remove("open-success");
}

function closeError(){
  document.querySelector("#error").classList.remove("open-error");
}

