import { Component, OnInit } from '@angular/core';
import { Review } from '../entity/review';
import { ProductdetailComponent } from '../productdetail/productdetail.component';
import { ReviewService } from '../service/review.service';
import { UserauthService } from '../service/userauth.service';

@Component({
  selector: 'app-review',
  templateUrl: './review.component.html',
  styleUrls: ['./review.component.css'],
})
export class ReviewComponent implements OnInit {
  temp1: any;
  userid: number;
  rating: number;
  review: any = [];
  overallRating: number = 0;
  addreview = new Review();
  editreview = new Review();
  arr = [1, 2, 3, 4, 5];
  reviewid: number;
  length: number = 0;
  productId: number;
  constructor(
    private reviewService: ReviewService,
    private productDetail: ProductdetailComponent,
    private userAuth: UserauthService
  ) {}

  ngOnInit(): void {
    let temp = this.userAuth.getUserData();
    this.userid = temp.id;
    this.productId = this.productDetail.getProductId();
    this.getReviews();
  }
  public getReviews() {
    this.reviewService.getReviews(this.productId).subscribe(
      (data) => {
        this.review = data;
        let sum = 0;
        console.log(this.review);
        for (let i = 0; i < this.review.length; i++) {
          sum += this.review[i].rating;
        }
        this.length = this.review.length;
        this.overallRating = sum / this.length;

        console.log(this.overallRating);
        if (isNaN(this.overallRating)) {
          this.overallRating = 0;
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }
  addReview() {
    let user = this.userAuth.getUserData();
    this.addreview.productid = this.productDetail.getProductId();
    this.addreview.userid = user.id;
    this.addreview.rating = this.rating;
    console.log(this.addreview);
    this.reviewService.addReview(this.addreview).subscribe(
      (data) => {
        console.log(data);
        this.reloadData();
      },
      (error) => {
        console.log(error);
      }
    );
  }
  reloadData() {
    setTimeout(function () {
      location.reload();
    }, 800);
  }
  getreview(id: number) {
    this.reviewService.getReview(id).subscribe(
      (data) => {
        this.temp1 = data;
        this.editreview = this.temp1;
        this.reviewid = this.editreview.id;
      },
      (error) => {
        console.log(error);
      }
    );
  }
  editReview() {
    this.reviewService.editReview(this.editreview).subscribe((data) => {
      console.log(data);
      this.reloadData();
    });
  }
  deleteReview() {
    this.reviewService.deleteReview(this.reviewid).subscribe(
      (data) => {
        console.log(data);
        this.reloadData();
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
