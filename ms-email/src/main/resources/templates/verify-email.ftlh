<!DOCTYPE html>
<html lang="pt">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Verificação de email</title>
    <style>
      * {
        margin: 0;
        padding: 0;
      }

      h1 {
        background-color: #403ffb;
        text-align: center;
        padding: 15px;
        color: #fff;
      }

      p {
        padding: 15px;
      }

      p span {
        color: #403ffb;
        font-weight: bold;
      }

      p a {
        background-color: #403ffb;
        padding: 10px;
        border-radius: 5px;
        color: #fff;
        font-weight: bold;
        text-decoration: none;
      }

      img {
        width: 100%;
        height: 35vh;
        position: absolute;
        bottom: 0;
      }
    </style>
  </head>
  <body>
    <h1>Path2</h1>
    <p>
      Olá ${name}! Este email está a ser enviado para confirmares o teu email, se
      não foi você quem solicitou pode ignorar.
    </p>
    <p>Enviamos abaixo o seu link de confirmação:</p>
    <p><a href="${verifyLink}" target="_blank">Clique aqui 😊</a></p>
    <p>
      Atenciosamente, <br />
      Equipa do <span>Path2</span>
    </p>
  </body>
</html>
