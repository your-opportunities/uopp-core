package ed.uopp.uoppcore.entity;

public enum SubscriptionChannel {

    EMAIL, TELEGRAM;

    @Override
    public String toString() {
        return this.name();
    }

}
