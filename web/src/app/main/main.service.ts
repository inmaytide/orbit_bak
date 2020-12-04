export class MainService {
    isCollapsed = false;

    toggleCollapsed() {
        this.isCollapsed = !this.isCollapsed;
    }
}
