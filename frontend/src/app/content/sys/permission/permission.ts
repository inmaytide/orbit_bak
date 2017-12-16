import { Component, OnInit } from "@angular/core";
import { PermissionService } from "./permission.service";
import { Permission } from "../../../models/permission";
import { CommonUtils } from "../../../common-utils";
import { NzModalService } from "ng-zorro-antd";
import { PermissionInfoComponent } from "./permission.info";
import { TranslateService } from "@ngx-translate/core";
import { DataDictionaryService } from "../data-dictionary/data-dictionary.service";

@Component({
    selector: 'permission',
    templateUrl: './permission.html'
})
export class PermissionComponent implements OnInit {
    private menus: Permission[] = [];
    private expandDataCache = {};

    constructor(private service: PermissionService,
        private modalService: NzModalService,
        private translate: TranslateService,
        private dictService: DataDictionaryService) {
    }

    ngOnInit() {
        this.service.list()
            .then(data => {
                this.menus = data;
                this.menus.forEach(item => {
                    item['categoryText'] = this.dictService.getText("permission.category", item.category);
                    item['methodText'] = this.dictService.getText("permission.method", item.method);
                    this.expandDataCache[item.id] = this.convertTreeToList(item);
                });
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
            width: 650
        });
        subscription.subscribe(result => {
            console.log(result);
        })
    }

}
