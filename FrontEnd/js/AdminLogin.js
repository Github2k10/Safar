let pwd = document.getElementById('pwd');
let eye = document.getElementById('eye');

eye.addEventListener('click',togglePass);

function togglePass(){

   eye.classList.toggle('active');

   (pwd.type == 'password') ? pwd.type = 'text' : pwd.type = 'password';
}

document.getElementById("submit").addEventListener("click", checkStuff);

function checkStuff(event) {
  event.preventDefault();
  let email = document.form1.email;
  let password = document.form1.password;
  let msg = document.getElementById('msg');
  
  if (email.value == "") {
    msg.style.display = 'block';
    msg.innerHTML = "Please enter your email";
    email.focus();
    return false;
  } else {
    msg.innerHTML = "";
  }
  
  if (password.value == "") {
    msg.innerHTML = "Please enter your password";
    password.focus();
    return false;
  } else {
    msg.innerHTML = "";
  }
  
  let re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  if (!re.test(email.value)) {
    msg.innerHTML = "Please enter a valid email";
    email.focus();
    return false;
  } else {
    msg.innerHTML = "";
  }

  let obj = {
    "email": email.value,
    "password": password.value
  }

  loginAdmin(obj)
}



async function loginAdmin(obj){
  try {      
      const res = await fetch("http://localhost:8080/safar/admin/login",{
          method: 'POST',
          body: JSON.stringify(obj),
          headers: {
              "Content-Type": "application/json"
          }
      });

      const out = await res.json();
      
      if(res.ok){
        setTimeout(function() {
          const jsonData = JSON.stringify(out);
          localStorage.setItem("adminData", jsonData);
          window.location.href = "./admin.html";
        }, 500); 
      }
      else{
          alert(out.message)
      }
      

  } catch (error) {
      alert(error);
  }
}