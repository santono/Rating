package rating.dto;


import java.util.List;

public class PodrTreeDTO {

    private PodrEntityDTO podrEntityDTO;
    List<PodrTreeDTO> nodes;

    public PodrEntityDTO getPodrEntityDTO() {
        return podrEntityDTO;
    }

    public void setPodrEntityDTO(PodrEntityDTO podrEntityDTO) {
        this.podrEntityDTO = podrEntityDTO;
    }

    public List<PodrTreeDTO> getNodes() {
        return nodes;
    }

    public void setNodes(List<PodrTreeDTO> nodes) {
        this.nodes = nodes;
    }
}
