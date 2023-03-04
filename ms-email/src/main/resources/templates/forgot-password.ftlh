<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <title>Recuperação de credenciais</title>
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
    <p>Olá ${name}, você esqueceu a sua senha? Não fique triste, podes criar uma nova clicando aqui: </p>

    <a href="http://localhost:8080/api/v1/forgot?t=${token}">Clique aqui bro</a>

    <p>
       Atenciosamente, <br />
       Equipa do <span>Path2</span>
    </p>
</body>
</html>