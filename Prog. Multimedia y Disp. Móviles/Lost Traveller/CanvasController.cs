using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class CanvasController : MonoBehaviour
{
    private PointsController pointsController;

    // Start is called before the first frame update
    void Start()
    {
        pointsController = FindAnyObjectByType<PointsController>();    
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public void Restart()
    {
        SceneManager.LoadScene("Level_1");
    }

    public void ExitGame() 
    {
        Application.Quit();
    }

    public void ResetMaxScore()
    {
        pointsController.ResetMaxPoints();
    }
}
