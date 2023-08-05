const popupButton = document.getElementById('popupButton');
const popupMessage = document.getElementById('popupMessage');

popupButton.addEventListener('click', () => {
  popupMessage.style.display = 'block';
});

function closePopup() {
  popupMessage.style.display = 'none';
}


document.querySelector("#signout").addEventListener("click", logout);

async function logout(){
  try {      

    let loginData = JSON.parse(localStorage.getItem("adminData"))

    const res = await fetch(`http://localhost:8080/safar/admin/logout?key=${loginData.aid}`,{
        method: 'POST',
        headers: {
            "Content-Type": "application/json"
        }
    });
    
    if(res.ok){
      console.log("Logout...");

      setTimeout(function() {
        localStorage.removeItem("adminData");
        window.location.href = "./AdminLogin.html";
      }, 500); 
    }
    else{
        alert(out.message)
    }
    

  } catch (error) {
      alert(error);
  }
}

