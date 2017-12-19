import { Component, OnInit, ViewChild } from "@angular/core";
import { PermissionService } from "./permission.service";
import { Permission } from "../../../models/permission";
import { CommonUtils } from "../../../common-utils";
import { NzModalService } from "ng-zorro-antd";
import { PermissionInfoComponent } from "./permission.info";
import { TranslateService } from "@ngx-translate/core";

@Component({
    selector: 'permission',
    templateUrl: './permission.html'
})
export class PermissionComponent implements OnInit {

    private menus: Permission[] = [];
    private expandDataCache = {};

    constructor(private service: PermissionService,
        private modalService: NzModalService,
        private translate: TranslateService) {
    }

    ngOnInit() {
        this.getData(new Array());
    }

    getData(expandPaths: string[]) {
        this.service.list()
            .then(data => {
                this.menus = data;
                this.menus.forEach(item => {
                    this.expandDataCache[item.id] = this.convertTreeToList(item);
                });

                if (expandPaths && expandPaths.length > 0) {
                    this.menus.forEach(item => {
                        if (expandPaths[0] == item.id) {
                            item['expand'] = true;
                        }
                    })

                }
            })
            .catch(reason => CommonUtils.handleErrors(reason))
    }

    collapse(array, data, $event) {
        if ($event === false) {
            if (data.children) {
                data.children.forEach(d => {
                    const target = array.find(a => a.id === d.id);
                    target.expand = false;
                    this.collapse(array, target, false);
                });
            } else {
                return;
            }
        }
    }

    convertTreeToList(root) {
        const stack = [], array = [], hashMap = {};
        stack.push({ ...root, level: 0, expand: false });

        while (stack.length !== 0) {
            const node = stack.pop();
            this.visitNode(node, hashMap, array);
            if (node.parent == -1) {
                delete node.parent;
            }
            if (node.children) {
                for (let i = node.children.length - 1; i >= 0; i--) {
                    stack.push(Object.assign(node.children[i], { level: node.level + 1, expand: false, parent: node }));
                }
            }
        }
        return array;
    }

    visitNode(node, hashMap, array) {
        if (!hashMap[node.id]) {
            hashMap[node.id] = true;
            array.push(node);
        }
    }


    add() {
        const subscription = this.modalService.open({
            title: this.translate.getParsedResult({}, "permission.modal.title"),
            content: PermissionInfoComponent,
            footer: false,
            maskClosable: false,
            showConfirmLoading: true,
            width: 650,
            onOk() {
                
            }
        });

        subscription.subscribe(result => {
            if (typeof(result) == "object") {
                this.getData(result as string[]);
            }
        })
    }

    remove(inst: Permission) {
        this.modalService.confirm({
            title: "Prompt",
            content: "Confirm to remove the permission your selected?",
            onOk: (function (service) {
                return function () {
                    service.remove(inst.id)
                        .then(response => {
                            console.log(response);
                        })
                        .catch(reason => CommonUtils.handleErrors(reason))
                }
            })(this.service)
        })
    }

}
