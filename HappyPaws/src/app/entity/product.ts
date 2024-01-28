import { Category } from './category';

export class Product {
  productid: number;
  productName: String;
  price: String;
  imageUrl: String;
  quantity: number;
  description: String;
  productCategory: Category;
  wishlist: boolean;
}
