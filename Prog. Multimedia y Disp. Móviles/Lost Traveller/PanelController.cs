using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class PanelController : MonoBehaviour
{
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public void StartGame()
    {
        // Cambia a la escena llamada "Level_1" cuando se llame a este m�todo.
        SceneManager.LoadScene("Level_1");
    }

    public void QuitGame()
    {
        // Sale de la aplicaci�n cuando se llame a este m�todo.
        Application.Quit(); 
    }
}
