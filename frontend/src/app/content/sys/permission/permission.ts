import { Component, OnInit, ViewChild } from "@angular/core";
import { PermissionService } from "./permission.service";
import { Permission } from "../../../models/permission";
import { CommonUtils } from "../../../common-utils";
import { NzModalService } from "ng-zorro-antd";
import { PermissionInfoComponent } from "./permission.info";
import { TranslateService } from "@ngx-translate/core";
import { isUndefined } from "util";

@Component({
    selector: 'permission',
    templateUrl: './permission.html',
    styles: [
        `
        .func {
            display: inline-block;
        }
        `
    ]
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

    private autoExpand(expandPaths: string[], data: Permission[], index: number) {
        if (expandPaths && expandPaths.length > 0 && expandPaths[0] !== "-1") {
            data.forEach(item => {
                if (expandPaths[index] == item.id) {
                    item['expand'] = true;
                    const children = item.children
                    if (children && children.length > 0) {
                        this.autoExpand(expandPaths, children, ++index);
                    }
                }
            })
        }
    }

    getData(expandPaths: string[]) {
        this.service.list()
            .then(data => {
                this.autoExpand(expandPaths, data, 0);
                data.forEach(item => {
                    this.expandDataCache[item.id] = this.convertTreeToList(item);
                });
                this.menus = data;
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
        stack.push({ ...root, level: 0, expand: root.expand });

        while (stack.length !== 0) {
            const node = stack.pop();
            this.visitNode(node, hashMap, array);
            if (node.parent == -1) {
                delete node.parent;
            }
            if (node.children) {
                for (let i = node.children.length - 1; i >= 0; i--) {
                    stack.push(Object.assign(node.children[i], { level: node.level + 1, parent: node, expand: node.children[i].expand }));
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
            componentParams: { state: 'add' },
            footer: false,
            maskClosable: false,
            showConfirmLoading: true,
            width: 650,
            onOk() {

            }
        });

        subscription.subscribe(result => {
            if (typeof (result) == "object") {
                this.getData(result as string[]);
            }
        })
    }

    private getExpandPaths(inst: any, expandPaths: string[]): string[] {
        if (isUndefined(inst)) {
            return expandPaths.reverse().slice(0, expandPaths.length - 1);
        }
        expandPaths.push(inst.id);
        return this.getExpandPaths(inst.parent, expandPaths);
    }

    details(inst: Permission) {
        const subscription = this.modalService.open({
            title: this.translate.getParsedResult({}, "permission.modal.title"),
            content: PermissionInfoComponent,
            componentParams: { inst: inst, state: 'details' },
            footer: false,
            maskClosable: false,
            showConfirmLoading: true,
            width: 650
        });
    }

    remove(inst: Permission) {

        const expandPaths = this.getExpandPaths(inst, []);

        this.modalService.confirm({
            title: this.translate.getParsedResult({}, "layer.title.prompt"),
            content: this.translate.getParsedResult({}, "permission.remove.confirm.message"),
            onOk: (function (that) {
                return function () {
                    that.service.remove(inst.id)
                        .then(response => {
                            that.getData(expandPaths);
                        })
                        .catch(reason => CommonUtils.handleErrors(reason))
                }
            })(this)
        })
    }

    edit(inst: Permission) {
        const expandPaths = this.getExpandPaths(inst, []);
        const subscription = this.modalService.open({
            title: this.translate.getParsedResult({}, "permission.modal.title"),
            content: PermissionInfoComponent,
            componentParams: { inst: inst, state: 'edit'},
            footer: false,
            maskClosable: false,
            showConfirmLoading: true,
            width: 650,
            onOk() {
                
            }
        });
    }

}
