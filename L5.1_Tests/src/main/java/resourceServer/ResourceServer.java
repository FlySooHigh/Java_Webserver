package resourceServer;

import resources.TestResource;

public class ResourceServer implements ResourceServerMBean {
    private TestResource testResource;

    public ResourceServer(TestResource testResource) {
        this.testResource = testResource;
    }

    @Override
    public String getName() {
        return testResource.getName();
    }

    @Override
    public int getAge() {
        return testResource.getAge();
    }

    public void setResource(TestResource testResource){
        this.testResource = testResource;
    }
}
