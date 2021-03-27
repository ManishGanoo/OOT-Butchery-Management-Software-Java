package BackEnd;

/**
 * Created by erosennin on 3/11/17.
 */
public enum Page {
    HOME{
        @Override
        public String toString(){
            return "home";
        }
    }
    ,SALES{
        @Override
        public String toString(){
            return "Sales";
        }
    }
    ,VIEWSALES{
        @Override
        public String toString(){
            return "viewsales";
        }
    }
    ,ADDFORM{
        @Override
        public String toString(){
            return "addform";
        }
    }
    ,PRODUCT{
        @Override
        public String toString(){
            return "product";
        }
    }
    ,MANAGE{
        @Override
        public String toString(){
            return "manage";
        }
    }
    ,SUPPLIER{
        @Override
        public String toString(){
            return "supplier";
        }
    }
    ,CLIENT{
        @Override
        public String toString(){
            return "customer";
        }
    }
    ,LOGIN{
        @Override
        public String toString(){
            return "login";
        }
    }
    ,PURCHASES{
        @Override
        public String toString(){
            return "purchases";
        }
    }
    ,PAYMENT{
        @Override
        public String toString(){
            return "payment";
        }
    }
    ,INVOICE{
        @Override
        public String toString(){
            return "invoice";
        }
    }
}
