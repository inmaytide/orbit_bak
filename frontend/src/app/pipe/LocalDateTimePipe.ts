import {Pipe, PipeTransform} from "@angular/core";
import {DatePipe} from "@angular/common";

@Pipe({
  name: 'localDateTime'
})
export class LocalDateTimePipe implements PipeTransform {
  transform(value: any, ...args: any[]) {
    let arr = value as number[];
    let date = new Date();
    date.setFullYear(arr[0], arr[1] - 1, arr[2]);
    date.setHours(arr[3], arr[4], arr[5] == null ? 0 : arr[5]);
    return new DatePipe("en").transform(date, 'y-MM-dd HH:mm:ss');
  }

}
