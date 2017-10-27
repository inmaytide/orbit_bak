import { WebappFrontendPage } from './app.po';

describe('webapp-frontend App', () => {
  let page: WebappFrontendPage;

  beforeEach(() => {
    page = new WebappFrontendPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
