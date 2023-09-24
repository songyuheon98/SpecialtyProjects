document.getElementById('signup-form').addEventListener('submit', function(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    fetch('/api/auth/signup', {
        method: 'POST',
        body: JSON.stringify({
            username: formData.get('username'),
            nickname: formData.get('nickname'),
            password1: formData.get('password1'),
            password2: formData.get('password2')
        }),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => response.json()).then(data => {
        alert(data.message);
    });
});

document.getElementById('update-form').addEventListener('submit', function(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    fetch('/api/auth/update', {
        method: 'PUT',
        body: JSON.stringify({
            nickname: formData.get('nickname')
        }),
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(response => response.json()).then(data => {
        alert(data.message);
    });
});
