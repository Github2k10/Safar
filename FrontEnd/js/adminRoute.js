let loginData = JSON.parse(localStorage.getItem("adminData"))

fetch(`http://localhost:8080/safar/route/all`)
  .then(response => response.json())
  .then(data => {

    console.log(data)
    
    data.forEach(element => {
        document.querySelector(".UserData").innerHTML += `
        <tr>
            <td class="column1">${element.routeID}</td>
            <td class="column4">${element.routeFrom}</td>
            <td class="column5">${element.routeTo}</td>
            <td class="column7">${element.distance}</td>
            <td id="btn" class="column8">
                <i class="fi fi-rs-trash"></i>
            </td>
        </tr>
        `
    });
  })
  .catch(error => {
    console.log('Error:', error);
  });