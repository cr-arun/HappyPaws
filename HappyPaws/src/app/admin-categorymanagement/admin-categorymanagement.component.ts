import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from '../entity/category';
import { CategoryService } from '../service/category.service';

@Component({
  selector: 'app-admin-categorymanagement',
  templateUrl: './admin-categorymanagement.component.html',
  styleUrls: ['./admin-categorymanagement.component.css'],
})
export class AdminCategorymanagementComponent implements OnInit {
  public page = 1;
  public pageSize = 3;
  public length: number;
  categoryId: number;
  categorys: Array<Category>;
  isShown: boolean = false;
  category = new Category();
  category1 = new Category();
  cat: Category;
  constructor(
    private categoryService: CategoryService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.categoryService.getCategoryList().subscribe(
      (data) => {
        this.categorys = data;
        this.length = this.categorys.length;
        console.log(this.categorys);
      },
      (error) => console.log(error)
    );
  }

  reloadData() {
    setTimeout(function () {
      location.reload();
    }, 800);
  }

  listCategorys() {
    this.categoryService.getCategoryList().subscribe(
      (data) => {
        this.categorys = data;
      },
      (error) => console.log(error)
    );
  }

  show(tempId: number) {
    this.categoryId = tempId;
    this.isShown = true;
    this.categoryService.getCategoryById(this.categoryId).subscribe(
      (data) => {
        Object.assign(this.category, data);
        console.log(this.category);
      },
      (error) => console.log(error)
    );
  }
  back() {
    this.isShown = false;
  }

  newCategory() {
    this.categoryService.addnewCategory(this.category1).subscribe(
      (data) => {
        console.log('Done');

        this.category1 = new Category();
        this.reloadData();
      },
      (error) => {
        console.log('Not Done');
      }
    );
  }

  editCategoryLocal() {
    this.categoryService.editCategory(this.categoryId, this.category).subscribe(
      (data) => {
        console.log(data);
        this.category = new Category();

        this.reloadData();
      },
      (error) => {
        console.log(error);
      }
    );
  }
  deleteCategory() {
    this.categoryService.deleteCategoryById(this.categoryId).subscribe(
      (data) => {
        console.log('deleted');

        this.reloadData();
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
