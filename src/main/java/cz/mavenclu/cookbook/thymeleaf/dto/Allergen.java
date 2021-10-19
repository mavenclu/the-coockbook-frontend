package cz.mavenclu.cookbook.thymeleaf.dto;

public enum Allergen {


    GLUTEN("A1  - gluten"){
        @Override
        public String getDescription(){
            return "In food made with flour such as pasta and bread.";
        }
    },

    CRUSTACEANS("A2 - crustaceans"){
        @Override
        public String getDescription(){
            return "Such as crab, lobster, prawns, shrimp and scampi.";
        }
    },

    EGGS("A3 - eggs"){
        @Override
        public String getDescription(){
            return "Can be found in cakes, sauces and pastries.";
        }
    },

    FISH("A4 - fish"){
        @Override
        public String getDescription(){
            return "Found in pizza, dressings and Worcestershire sauce.";
        }
    },

    PEANUTS("A5 - peanuts"){
        @Override
        public String getDescription(){
            return "Can be found in cakes, biscuits and squid.";
        }
    },

    SOYA("A6 - soya"){
        @Override
        public String getDescription(){
            return "Various beans including edamame and tofu.";
        }
    },

    MILK("A7 - milk and lactose"){
        @Override
        public String getDescription() {
            return "Butter, cheese, cream and milk powders contain milk.";
        }
    },

    NUTS("A8 - nuts"){
        @Override
        public String getDescription() {
            return "Including cashews, almonds and hazelnuts.";
        }
    },

    CELERY("A9 - celery"){
        @Override
        public String getDescription(){
            return "Including stalks, leaves, seeds and celeriac.";
        }
    },

    MUSTARD("A10 - mustard"){
        @Override
        public String getDescription(){
            return "Can be in liquid or powder form as well as seeds.";
        }
    },

    SESAME_SEEDS("A11 - sesame seeds"){
        @Override
        public String getDescription() {
            return "Found on burgers, bread sticks and salads.";
        }
    },

    SULFITE("A12 - sulphur dioxide and sulfites"){
        @Override
        public String getDescription(){
            return "Found in dried fruit like raisins and some drinks.";
        }
    },
    LUPIN("A13 - lupin and products thereof"){
        @Override
        public String getDescription() {
            return "Lupin can be found in bread, pastries and pasta.";
        }
    },
    MOLLUSCS("A14 - molluscs and products thereof"){
        @Override
        public String getDescription() {
            return "Including land snails, whelks and squid.";
        }
    },
    HISTAMINE("H - histamine"){
        @Override
        public String getDescription() {
            return "Found in fermented foods, cheese, yogurt, processed meats, canned foods, vinegars, alcoholic drinks.";
        }

    };



    private String label;


    Allergen(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
    public String getDescription() {
        return "Food allergen";
    }
}