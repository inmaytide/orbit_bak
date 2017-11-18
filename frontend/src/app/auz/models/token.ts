export class Token {
  username: string;
  password: string;
  captcha: string;
  grant_type: string = "password";
  client_id: string = "login";
  client_secret: string = "59a84cbf83227a35";
  scope: string = "all";
}
