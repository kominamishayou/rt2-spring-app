document.addEventListener('DOMContentLoaded', function () {
  const passInput = document.getElementById('empPass');
  const showCheck = document.getElementById('showPassword');

  showCheck.addEventListener('change', function () {
    passInput.type = this.checked ? 'text' : 'password';
  });
});
