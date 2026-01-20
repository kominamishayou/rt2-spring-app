document.addEventListener("DOMContentLoaded", () => {

  const passInput = document.getElementById("empPass");
  const confirmBlock = document.getElementById("password_confirm");
  const confirmInput = document.getElementById("password_confirm_input");
  const errMsg = document.getElementById("different_password_error_msg");
  const form = document.getElementById("form");

  // パスワード入力で確認欄表示
  passInput.addEventListener("input", () => {
    if (passInput.value.length > 0) {
      confirmBlock.style.display = "flex";
    }
  });

  // 表示切替
  window.changeType = () => {
    passInput.type = passInput.type === "password" ? "text" : "password";
  };

  window.changeTypeConfirm = () => {
    confirmInput.type = confirmInput.type === "password" ? "text" : "password";
  };

  // 一致チェック
  window.checkPassword = () => {
    if (passInput.value === confirmInput.value) {
      form.submit();
    } else {
      errMsg.style.display = "inline";
    }
  };
});
